package com.utils;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * @author monetto
 */
@Component
public class JsonWebTokenUtil {

    public static long KEEP_TIME;
    public static String SECRET_KEY;
    private final static JwtParser JWT_PARSER = Jwts.parser();

    public static String generateToken(String audience, String ip) {
        byte[] scriptKeyData = DatatypeConverter.parseBase64Binary(SECRET_KEY + ip);
        long currentTimeMillis = System.currentTimeMillis();
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        Date now = new Date(currentTimeMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setAudience(audience)
                .signWith(hs256, new SecretKeySpec(scriptKeyData, hs256.getJcaName()));
        builder.setExpiration(new Date(currentTimeMillis + KEEP_TIME));
        return builder.compact();
    }

    public static Claims parseToken(String token, String ip) {
        Claims claims;
        try {
            claims = JWT_PARSER.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY + ip))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

}
