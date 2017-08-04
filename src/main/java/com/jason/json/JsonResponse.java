package com.jason.json;

import com.jason.enumeration.ErrorMsgEnum;

/**
 * 服务器端响应json转换类。
 * <p/>
 */
public class JsonResponse implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private static Integer SUCCESS_CODE = 2000;
	private static String SUCCESS_MSG = "操作成功";	
	
	private static Integer ERROR_CODE = 5000;
	private static String ERROR_MSG = "稍后重试";

	State state;
	Object data;

	public JsonResponse() {
		state = new State();
		data = null;
	}

	public static class State
	{
		private Integer code ;
		private String msg;

		public static State newOk() {
			State state = new State();
			state.setCode(SUCCESS_CODE);
			state.setMsg(SUCCESS_MSG);
			return state;
		}

		public static State newError() {
			State state = new State();
			state.setCode(ERROR_CODE);
			state.setMsg(ERROR_MSG);
			return state;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		
	}
	
	/**
	 * 实例一个成功的响应，状态码为：2000000（具体以字典为准），状态描述使用字典中的定义。
	 *
	 * @return 响应实例
	 */
	public static JsonResponse newOk() {
		JsonResponse response = new JsonResponse();
		response.setState(State.newOk());
		return response;
	}

	/**
	 * 实例一个包括数据的成功响应，状态码为：2000000（具体以字典为准），状态描述使用字典中的定义。
	 *
	 * @param data 数据
	 * @return 响应实例
	 */
	public static JsonResponse newOk(Object data) {
		JsonResponse response = new JsonResponse();
		response.setData(data);
		response.setState(State.newOk());
		return response;
	}


	/**
	 * 实例化一个自定义状态信息的响应，状态描述使用字典中的定义。
	 *
	 * @param data      数据
	 * @param state 自定义的状态信息
	 * @return 响应实例
	 */
	public static JsonResponse newInstance(State state, Object data) {
		JsonResponse response = new JsonResponse();
		response.setData(data);
		response.setState(state);
		return response;
	}


	/**
	 * 使用自定义的状态信息，实例化一个错误的响应，响应信息对应字典中的信息。
	 *
	 * @return 响应实例
	 */
	public static JsonResponse newError() {
		JsonResponse response = new JsonResponse();
		response.setState(State.newError());
		return response;
	}
	
	public static JsonResponse newError(Integer code, String msg) {
		JsonResponse response = new JsonResponse();
		State state = new State();
		state.setCode(code);
		state.setMsg(msg);
		response.setState(state);
		return response;
	}
	
	public static JsonResponse newError(String msg) {
		JsonResponse response = new JsonResponse();
		State state = new State();
		state.setCode(ERROR_CODE);
		state.setMsg(msg);
		response.setState(state);
		return response;
	}
	
	public static JsonResponse newError(ErrorMsgEnum errorMsgEnum) {
		JsonResponse response = new JsonResponse();
		State state = new State();
		state.setCode(errorMsgEnum.getCode());
		state.setMsg(errorMsgEnum.getUserMsg());
		response.setState(state);
		return response;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	
}
