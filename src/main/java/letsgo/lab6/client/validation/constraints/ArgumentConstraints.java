package letsgo.lab6.client.validation.constraints;

import letsgo.lab6.client.validation.DataType;

public class ArgumentConstraints extends Constraints {

    private final boolean furtherInputRequired;

    public ArgumentConstraints() {
        super();
        furtherInputRequired = false;
    }

    public ArgumentConstraints(DataType dataType) {
        super(dataType);
        furtherInputRequired = false;
    }

    public ArgumentConstraints(DataType dataType, boolean furtherInputRequired) {
        super(dataType);
        this.furtherInputRequired = furtherInputRequired;
    }

    public ArgumentConstraints(boolean furtherInputRequired) {
        super();
        this.furtherInputRequired = furtherInputRequired;
    }

    public ArgumentConstraints(DataType type, boolean nullable, boolean positive, boolean furtherInputRequired) {
        super(type, nullable, positive);
        this.furtherInputRequired = furtherInputRequired;
    }

    public boolean isFurtherInputRequired() {
        return furtherInputRequired;
    }

}
