# exceptionHandler 
# 切面异常处理

### 存在问题
* 日常业务中存在如下业务代码
* 他们使用大量的try/catch来捕获异常
* 导致整个控制层代码可读性极差，并且此类工作重复枯燥、容易被遗忘。
* 一份糟糕的控制器代码如下：
```
@RequestMapping("test/run/old")
public JsonResponse testRunOld() {
	try {
		exampleService.runTest();
		System.out.println("正常运行");
		return JsonResponse.newOk();
	}catch (DataNotCompleteException e) {
		logger.error("something error occured!");
		return JsonResponse.newError(ErrorMsgEnum.DATA_NO_COMPLETE);
	} catch (Exception e) {
		return JsonResponse.newError();
	}
	
}
```

### 解决方案
* 我们将采用AOP统一处理异常
* 使用一个自定义异常和一个错误前端提示枚举来逐层传递消息
* 一个错误枚举来代替新建异常类，减少文件数量

#### 几个核心类代码
* ErrorMsgEnum 错误枚举
```
public enum ErrorMsgEnum {
	
	SUCCESS(true, 2000,"正常返回", "操作成功"), 
	
	// 系统错误，50开头
	SYS_ERROR(false, 5000, "系统错误", "系统错误"),
	PARAM_INVILAD(false, 5001, "参数出现异常", "参数出现异常"), 
	DATA_NO_COMPLETE(false, 5002, "数据填写不完整，请检查", "数据填写不完整，请检查");

	private ErrorMsgEnum(boolean ok, int code, String msg ,String userMsg) {
		this.ok = ok;
		this.code = code;
		this.msg = msg;
		this.userMsg = userMsg;
	}

	private boolean ok;
	private int code;
	private String msg;
	private String userMsg;
}
```

* 控制层返回结果POJO类
```
public class JsonResponse{

	String msg;
	Object data;

	public JsonResponse() {
		msg = "";
		data = null;
	}
	
	public static JsonResponse newOk() {
		JsonResponse response = new JsonResponse();
		response.setState(State.newOk());
		return response;
	}
	
	public static JsonResponse newOk(Object data) {
		JsonResponse response = new JsonResponse();
		response.setData(data);
		response.setState(State.newOk());
		return response;
	}
	
	public static JsonResponse newError() {
		JsonResponse response = new JsonResponse();
		response.setMsg("无情的系统异常!");
		return response;
	}
	
	public static JsonResponse newError(ErrorMsgEnum errorMsgEnum) {
		JsonResponse response = new JsonResponse();
		state.setMsg(errorMsgEnum.getErrorMsg());
		return response;
	}
}
```

* 自定义异常类
```
public class CustomException extends Exception {

	private ErrorMsgEnum errorMsgEnum;

	public CustomException(ErrorMsgEnum errorMsgEnum) {
		this.errorMsgEnum = errorMsgEnum;
	}
}
```

* AOP捕获异常处理类
```
@Around("execution(public * com.jason.*.controller..*.*(..))")
public JsonResponse serviceAOP(ProceedingJoinPoint pjp) throws Exception {

	JsonResponse newResultVo = null;

	try {
		return (JsonResponse) pjp.proceed();
	} catch (CustomException e) {
		logger.info("自定义业务异常:" + e.getMessage());
		ErrorMsgEnum errorMsgEnum = e.getErrorMsgEnum();
		if (Objects.nonNull(errorMsgEnum)) {
			newResultVo = JsonResponse.newError(errorMsgEnum);
		} else {
			newResultVo = JsonResponse.newError(e.getMessage());	
		}
	} catch (Throwable e) {
		logger.error("出现运行时异常：", e);
		newResultVo = JsonResponse.newError();
	}

	return newResultVo;

}
```
### Test && End
至此，我们已经可以直接在Service中随意抛出一个异常，
将每个控制器层抛出的异常定义为throws Exception，我们的代码将会变成这样：
```
@Controller
public class TestController {
	
	@Autowired
	private IExampleService exampleService;
	
	@RequestMapping("test/run/aop")
	public JsonResponse testRunAop() throws Exception {
		exampleService.runTest();
		System.out.println("正常运行");
		return JsonResponse.newOk();
	}
}
```
```
@Service
public class ExampleService implements IExampleService{

	@Override
	public void runTest() throws Exception {

		// do something
		System.out.println("run something");
		throw new CustomException(ErrorMsgEnum.DATA_NO_COMPLETE);
	}

}
```
#### 经过这次处理：
* 再也不用去担心控制器层会不会捕获，
* 也不用为每一个异常特地去定义一个Exception。
* 最大的好处是：没有try/catch
* 一处代码，各种好处。
