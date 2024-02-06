package rocks.dnaka91.mabo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldIdTest {
	@Test
	void create() {
		assertEquals(FieldId.fromRaw(1 << 3), new FieldId(1, FieldEncoding.Varint));
	}
}
