package kiwi.core.storage.bitcask;

import kiwi.core.common.Bytes;

public record KeyHeader(Bytes key, Header header) {
}
