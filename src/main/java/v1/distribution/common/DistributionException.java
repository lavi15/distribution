package v1.distribution.common;

import lombok.Getter;


@Getter
public class DistributionException extends RuntimeException {
    private final DistributionErrors error;

    public DistributionException(DistributionErrors error, Throwable cause) {
        super(cause);
        this.error = error;
    }
}
