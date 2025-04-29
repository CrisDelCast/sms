package co.edu.icesi.pf.domain.model.gateways.notifications;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final long EXPIRATION_TIME_MS = TimeUnit.MINUTES.toMillis(5);

    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, OtpDetails> otpStorage = new HashMap<>();

    @Override
    public String generateOtp(String recipient) {
        String otp = String.format("%06d", secureRandom.nextInt(1000000)); 
        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME_MS;

        otpStorage.put(recipient, new OtpDetails(otp, expirationTime));
        return otp;
    }

    @Override
    public boolean validateOtp(String recipient, String otp) {
        OtpDetails otpDetails = otpStorage.get(recipient);

        if (otpDetails == null || System.currentTimeMillis() > otpDetails.getExpirationTime()) {
            return false; 
        }

        boolean isValid = otpDetails.getOtp().equals(otp);
        if (isValid) {
            otpStorage.remove(recipient); 
        }

        return isValid;
    }

    private static class OtpDetails {
        private final String otp;
        private final long expirationTime;

        public OtpDetails(String otp, long expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}
