package letsgo.lab6.client.validation;

public class Constraints {

    private final DataType dataType;
    private final boolean nullable;
    private final boolean positive;

    public Constraints() {
        dataType = DataType.STRING;
        nullable = true;
        positive = false;
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
