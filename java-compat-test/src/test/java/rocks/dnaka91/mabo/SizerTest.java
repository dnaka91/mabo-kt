package rocks.dnaka91.mabo;

import org.junit.jupiter.api.Test;
import rocks.dnaka91.mabo.buf.Sizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SizerTest {
	@Test
	void sizeU32() {
		assertEquals(1, Sizer.sizeU32(1));
		assertEquals(1, Sizer.sizeI32(1));
	}
}
