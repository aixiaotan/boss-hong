package com.boss.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	private ResponseUtil() {};

	public static void writeToClient(HttpServletResponse response, String jsonMsg) {
		
		PrintWriter out = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			out = response.getWriter();

			out.print(jsonMsg);

			response.flushBuffer();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
