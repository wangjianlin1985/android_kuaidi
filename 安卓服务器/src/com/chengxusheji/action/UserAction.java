package com.chengxusheji.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller @Scope("prototype")
public class UserAction {
	public String UserQuery(){
		return "user_query_view";
	}
}
