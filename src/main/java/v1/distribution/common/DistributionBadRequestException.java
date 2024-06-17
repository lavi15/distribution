package v1.distribution.common;

public class DistributionBadRequestException extends DistributionException {
    public DistributionBadRequestException(DistributionErrors error, Throwable cause) {
        super(error, cause);
    }

    public DistributionBadRequestException(DistributionErrors error) {
        super(error, null);
    }
}
