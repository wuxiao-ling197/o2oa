package com.x.base.core.entity.enums;

import com.x.base.core.entity.JpaObject;

/**
 * 平台设计搜索业务类型枚举类
 * script（脚本：流程平台脚本、内容管理脚本、门户脚本、服务管理的代理、服务管理的接口）
 * form（流程平台的表单、内容管理的表单）
 * page（门户的页面）
 * widget（门户的widget）
 * process（流程平台的流程模板）
 */
public enum DesignerType {

	script, form, page, widget, process;
	public static final int length = JpaObject.length_64B;
}
