package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.exceptions;

public class DeadGeneralException extends RuntimeException {
    public DeadGeneralException(String errorMessage) {
        super(errorMessage);
    }
}
