package letsgo.lab6.client.validation.constraints;

import letsgo.lab6.client.validation.DataType;

public abstract class Constraints {

    private final DataType dataType;
    private final boolean nullable;
    private final boolean positive;

    public Constraints() {
        dataType = DataType.STRING;
        nullable = true;
        positive = false;
    }

    public Constraints(DataType dataType) {
        this.dataType = dataType;
        nullable = false;
        positive = false;
    }

    public Constraints(DataType dataType, boolean nullable, boolean positive) {
        this.dataType = dataType;
        this.nullable = nullable;
        this.positive = positive;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isPositive() {
        return positive;
    }

}
