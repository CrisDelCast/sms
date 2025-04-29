package co.edu.icesi.pf.domain.usecase.notifications;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GenerateOtpUseCase {
    private static final int OTP_VALID_DURATION = 5 * 60; 
    private final Map<String, OtpEntry> otpStorage = new HashMap<>();

    public String execute(String email) {
        String otp = generateOtp(); 
        Instant expirationTime = Instant.now().plusSeconds(OTP_VALID_DURATION);
        
        otpStorage.put(email, new OtpEntry(otp, expirationTime));
        
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        OtpEntry otpEntry = otpStorage.get(email);

        if (otpEntry == null || Instant.now().isAfter(otpEntry.getExpirationTime())) {
            otpStorage.remove(email);
            return false;
        }

        boolean isValid = otpEntry.getOtp().equals(otp);
        if (isValid) {
            otpStorage.remove(email); 
        }
        return isValid;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); 
    }

    private static class OtpEntry {
        private final String otp;
        private final Instant expirationTime;

        public OtpEntry(String otp, Instant expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getExpirationTime() {
            return expirationTime;
        }
    }
}
