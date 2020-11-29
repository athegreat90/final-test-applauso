package com.applaudo.studios.moviestore.dto;

public class ResponseGenericDto<T>
{
    private Integer code;
    private String message;
    private T body;

    public ResponseGenericDto()
    {
    }

    public ResponseGenericDto(Integer code, String message, T body)
    {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getBody()
    {
        return body;
    }

    public void setBody(T body)
    {
        this.body = body;
    }
}
