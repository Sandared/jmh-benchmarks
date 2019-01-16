package io.jatoms.jmh;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ExampleProxy implements InvocationHandler{

    private Integer upperExclusive = 10;
    private Integer lowerExclusive = 0;
	private Receiver receiver;

    public ExampleProxy(Receiver receiver){
        this.receiver = receiver;
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Integer value = (Integer) args[0];
        if(value > lowerExclusive && value < upperExclusive )
            return method.invoke(receiver, args);
        else
            throw new IllegalArgumentException("Value was out of bounds: " + value);
	}
}
