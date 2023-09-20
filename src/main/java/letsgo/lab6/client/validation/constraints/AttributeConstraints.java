package letsgo.lab6.client.validation.constraints;

import letsgo.lab6.client.validation.DataType;

public class AttributeConstraints extends Constraints {

    private final String inviteMessage;
    private final String noArgumentMessage;
    private final String wrongArgumentTypeMessage;

    public AttributeConstraints(DataType dataType, boolean nullable, boolean positive,
                                String inviteMessage, String noArgumentMessage, String wrongArgumentTypeMessage) {
        super(dataType, nullable, positive);
        this.inviteMessage = inviteMessage;
        this.noArgumentMessage = noArgumentMessage;
        this.wrongArgumentTypeMessage = wrongArgumentTypeMessage;
    }

    public String getInviteMessage() {
        return inviteMessage;
    }

    public String getNoArgumentMessage() {
        return noArgumentMessage;
    }

    public String getWrongArgumentTypeMessage() {
        return wrongArgumentTypeMessage;
    }
}
