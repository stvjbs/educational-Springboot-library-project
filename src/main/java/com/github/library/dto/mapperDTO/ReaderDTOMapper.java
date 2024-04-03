package com.github.library.dto.mapperDTO;

import com.github.library.dto.ReaderDTO;
import com.github.library.entity.Reader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReaderDTOMapper {
    public Reader mapToReader(ReaderDTO readerDTO) {
        return new Reader(readerDTO.getName());
    }

    public ReaderDTO mapToReaderDTO(Reader reader) {
        ReaderDTO readerDTO = new ReaderDTO(reader.getName());
        readerDTO.setId(reader.getId());
        return readerDTO;
    }

    public List<ReaderDTO> mapToListDTO(List<Reader> list) {
        return list.stream().map(this::mapToReaderDTO).toList();
    }
}