package com.avocado.statistics.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.nio.ByteBuffer;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@MappedJdbcTypes(JdbcType.BINARY)
@Slf4j
public class UuidTypeHandler extends BaseTypeHandler<UUID> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        log.info("setNotNullParameter - uuid: {}", parameter);
        ps.setBytes(i, uuidToBytes(parameter));
    }

    private static byte[] uuidToBytes(UUID uuid) {
        log.info("uuidToBytes - uuid: {}", uuid);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    @Override
    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return bytesToUuid(rs.getBytes(columnName));
    }

    @Override
    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return bytesToUuid(rs.getBytes(columnIndex));
    }

    @Override
    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return bytesToUuid(cs.getBytes(columnIndex));
    }

    private static UUID bytesToUuid(byte[] bytes) {
        log.info("bytesToUuid - bytes: {}", bytes);
        if (bytes == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();

        UUID uuid = new UUID(high, low);
        log.info("uuid: {}", uuid);
        return uuid;
    }
}
