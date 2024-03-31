package com.github.library.dto.mapperDTO;

import com.github.library.dto.ReaderDTO;
import com.github.library.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderDTOMapper {
    public Reader mapToReader(ReaderDTO readerDTO) {
        return new Reader(readerDTO.getName());
    }

    public ReaderDTO mapToReaderDTO(Reader reader) {
        return ReaderDTO.builder()
                .name(reader.getName())
                .build();
    }
}