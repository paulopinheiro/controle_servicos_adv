package br.com.pereirakienast.controleservicos.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class LogicalException extends Exception {
    public LogicalException() {}
    public LogicalException (String msg) {super(msg);}
}
