package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.mapper.CompilationMapper;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.models.Compilation;
import ru.practicum.mainservice.repository.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationService {

    private final CompilationRepository compilationRepository;

    public List<CompilationDto> get(Boolean pinned, Integer from, Integer size) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Compilation> compilations;
        if (pinned != null) {
            compilations = compilationRepository.findAllByPinnedIs(pageRequest, pinned);
        } else
            compilations = compilationRepository.findAll(pageRequest).toList();

        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getById(Long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElse(null);
        if (compilation == null)
            throw new EntityNotFoundException("compilation " + compilationId + " not found");

        return CompilationMapper.toCompilationDto(compilation);
    }


    public CompilationDto save(NewCompilationDto compilationDto) {
        Compilation newCompilation = CompilationMapper.toCompilation(compilationDto);
        Compilation savedCompilation = compilationRepository.save(newCompilation);
        return CompilationMapper.toCompilationDto(savedCompilation);
    }

    public CompilationDto update(NewCompilationDto compilationDto, Long compilationId) {
        Compilation compilationToUpdate = compilationRepository.findById(compilationId).orElse(null);
        if (compilationToUpdate == null)
            throw new EntityNotFoundException("compilation " + compilationId + " not found");

        Compilation newCompilation = CompilationMapper.toCompilation(compilationDto);
        newCompilation.setId(compilationId);

        Compilation savedCompilation = compilationRepository.save(newCompilation);
        return CompilationMapper.toCompilationDto(savedCompilation);
    }

    public void delete(Long compilationId) {
        Compilation compilationToDelete = compilationRepository.findById(compilationId).orElse(null);
        if (compilationToDelete == null)
            throw new EntityNotFoundException("compilation " + compilationId + " not found");
        compilationRepository.delete(compilationToDelete);
    }
}
