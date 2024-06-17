package v1.distribution.common;

public class DistributionNotFoundException extends DistributionException {
    public DistributionNotFoundException(DistributionErrors error, Throwable cause) {
        super(error, cause);
    }

    public DistributionNotFoundException(DistributionErrors error) {
        super(error, null);
    }
}
