package com.boss.shar.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.boss.common.dto.ResponseCode;
import com.boss.common.dto.ResponseData;
import com.boss.shar.exception.IdeaXException;

@ControllerAdvice
public class IdeaXExceptionHandler {

	public ResponseData<String> errorHandler(HttpServletRequest req, IdeaXException e) {
		ResponseData<String> r = new ResponseData<String>();
		String sleuthVal = "org.springframework.cloud.sleuth.instrument.web.TraceRequestAttributes.TRACE_HANDLER";
		Object object = req.getAttribute(sleuthVal);
		if(object!=null) {
			r.setMDC(object.toString());
		}
		r.setMsg(e.getMessage());
		r.setCode(ResponseCode.BUSSINESS_ERROE.getCode());
		r.setUrl(req.getRequestURI().toString());
		return r;
	}
}
