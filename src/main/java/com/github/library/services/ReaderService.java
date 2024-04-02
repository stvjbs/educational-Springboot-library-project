package com.github.library.services;

import com.github.library.dto.ReaderDTO;
import com.github.library.dto.mapperDTO.ReaderDTOMapper;
import com.github.library.entity.Reader;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderDTOMapper readerDTOMapper;

    public List<ReaderDTO> getAllReaders() {
        List<ReaderDTO> list = new ArrayList<>();
        readerRepository.findAll().forEach(x -> list.add(readerDTOMapper.mapToReaderDTO(x)));
        return list;
    }

    public ReaderDTO getReaderById(long id) {
        Reader thisReader = readerRepository.findById(id).orElseThrow(NotFoundEntityException::new);
        return readerDTOMapper.mapToReaderDTO(thisReader);
    }

    public ReaderDTO addReader(String readerName) {
        Reader reader = new Reader(readerName);
        readerRepository.save(reader);
        return readerDTOMapper.mapToReaderDTO(reader);
    }

    public void deleteReader(long readerId) {
        readerRepository.deleteReaderById(readerId).orElseThrow(NotFoundEntityException::new);
    }
}
