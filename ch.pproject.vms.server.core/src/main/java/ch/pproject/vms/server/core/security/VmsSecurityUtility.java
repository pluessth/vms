package ch.pproject.vms.server.core.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.scout.rt.platform.security.SecurityUtility;
import org.eclipse.scout.rt.platform.util.Base64Utility;

public final class VmsSecurityUtility {

  public static String createPasswordHash(String password) {
    byte[] salt = SecurityUtility.createRandomBytes();
    byte[] hash = SecurityUtility.hash(toBytes(password.toCharArray()), salt);
    return String.format("%s.%s", Base64Utility.encode(salt), Base64Utility.encode(hash));
  }

  public static byte[] toBytes(final char[] data) {
    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      final OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_16);
      writer.write(data);
      writer.flush();
      return os.toByteArray();
    }
    catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
