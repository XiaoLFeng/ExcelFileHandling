package com.wxjw.dal.pojo;

import lombok.Getter;

/**
 * 错误码枚举列表
 *
 * @author 筱锋xiao_lfeng
 */
@Getter
public enum HttpCode {
    CONTINUE(100, "Continue", "继续。服务器已接收初始请求，客户端应继续发送其余部分。"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols", "切换协议。服务器已理解并接受客户端请求，将切换到协议升级的新协议"),
    PROCESSING(102, "Processing", "请求处理中，客户端根据协议进行处理。"),
    EARLY_HINTS(103, "Early Hints", "提前提示。服务器已经发送一些响应头部，等待发送正式的响应体。"),
    OK(200, "OK", "成功"),
    CREATED(201, "Created", "请求已完成，客户端将接收新请求。"),
    ACCEPTED(202, "Accepted", "已接受。请求已被服务器接受，但尚未执行或处理。"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information", "服务器认证失败，客户端将尝试新建连接。"),
    NO_CONTENT(204, "No Content", "当前请求没有实体数据，就是没有数据"),
    RESET_CONTENT(205, "Reset Content", "客户端将没有实体数据，直接返回一个Added  Removes Content"),
    PARTIAL_CONTENT(206, "Partial Content", "部分内容。服务器成功处理了部分请求的范围。"),
    MULTI_STATUS(207, "Multi-Status", "多个状态返回，如果当前请求是非回应，但存在多个状态"),
    ALREADY_REPORTED(208, "Already Reported", "已多次返回，删除进度失败。"),
    IM_USED(226, "IMUsed", "已被使用了，由于连接池由于找不到空闲"),
    MULTIPLE_CHOICES(300, "Multiple Choices", "支持多种协议。"),
    MOVE_PERMANENTLY(301, "Moved Permanently", "永久移动，重定向"),
    FOUND(302, "Found", "找到"),
    SEE_OTHER(303, "See Other", "客户端缓存的资源将发生变更，访问其他服务器。"),
    NOT_MODIFIED(304, "Not Modified", "服务器没有发送变更，客户端缓存的资源未发生变更。"),
    USE_PROXY(305, "Use Proxy", "客户端直接使用代理，不需要重定向"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect", "客户端将重定向到其他服务器"),
    PERMANENT_REDIRECT(308, "Permanent Redirect", "客户端将该资源转发给其他客户端"),
    BAD_REQUEST(400, "Bad Request", "请求数据格式不合法。"),
    UNAUTHORIZED(401, "Unauthorized ", "没有授权"),
    FORBIDDEN(403, "Forbidden", "没有权限"),
    NOT_FOUND(404, "Not Found", "资源未找到"),
    METHOD_NOT_ALLOW(405, "Method Not Allowed", "方法不被允许"),
    NOT_ACCEPTABLE(406, "Not Acceptable", "请求类型不合法"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", "代理认证失败"),
    REQUEST_TIMEOUT(408, "Request Timeout", "请求超时，超过最大超时限制。"),
    CONFLICT(409, "Conflict", "资源类型冲突"),
    LENGTH_REQUIRED(411, "Length Required", "需要长度"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "服务器内部错误"),
    NOT_IMPLEMENTED(501, "Not Implemented", "服务器不支持该协议"),
    BAD_GATEWAY(502, "Bad Gateway", "错误的网关"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", "服务器不可用"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout", "网关超时");

    /**
     * Http 状态码
     */
    private final int code;

    /**
     * 状态码信息概要
     */
    private final String message;

    /**
     * 描述模块
     */
    private final String description;

    HttpCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
