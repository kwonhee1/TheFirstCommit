package TheFirstCommit.demo.family;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FamilyCodeUtil {

    private static int FAMILY_CODE_LEN;

    public FamilyCodeUtil(@Value("${family.code-len}") int familyCodeLen) {
        this.FAMILY_CODE_LEN = familyCodeLen;
    }

    public static String encode(Long projectId) {
        String code = Long.toString(projectId, 36).toUpperCase();
        return String.format("%" + FAMILY_CODE_LEN + "s", code).replace(' ', '0');
    }

    // 코드 -> projectId 복원
    public static long decode(String code) {
        return Long.parseLong(code, 36);
    }

}
