package com.example.online_course.service;

import com.example.online_course.dto.request.SectionOcrTextRequest;
import com.example.online_course.dto.response.SectionOcrTextResponse;
import com.example.online_course.entity.SectionEntity;
import com.example.online_course.entity.SectionOcrTextEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.SectionOcrTextRepository;
import com.example.online_course.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SectionOcrTextServiceImpl implements SectionOcrTextService {
    private final SectionOcrTextRepository sectionOcrTextRepository;
    private final SectionRepository sectionRepository;

    @Override
    public SectionOcrTextResponse create(SectionOcrTextRequest request) {
        SectionEntity section = findSection(request.getSectionId());
        SectionOcrTextEntity entity = sectionOcrTextRepository.findBySection(section)
                .orElseGet(SectionOcrTextEntity::new);
        entity.setSection(section);
        return toResponse(apply(entity, request));
    }

    @Override
    @Transactional(readOnly = true)
    public SectionOcrTextResponse getById(Long id) {
        return toResponse(findOcrOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionOcrTextResponse> getAll() {
        return sectionOcrTextRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public SectionOcrTextResponse update(Long id, SectionOcrTextRequest request) {
        SectionOcrTextEntity entity = findOcrOrThrow(id);
        entity.setSection(findSection(request.getSectionId()));
        return toResponse(apply(entity, request));
    }

    @Override
    public void delete(Long id) {
        sectionOcrTextRepository.delete(findOcrOrThrow(id));
    }

    private SectionOcrTextEntity apply(SectionOcrTextEntity entity, SectionOcrTextRequest request) {
        entity.setSourceFile(request.getSourceFile());
        entity.setExtractedText(request.getExtractedText());
        entity.setConfidence(request.getConfidence());
        entity.setLanguage(request.getLanguage());
        SectionOcrTextEntity saved = sectionOcrTextRepository.save(entity);
        SectionEntity section = saved.getSection();
        if (section != null) {
            section.setOcrStatus("done");
            sectionRepository.save(section);
        }
        return saved;
    }

    private SectionEntity findSection(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));
    }

    private SectionOcrTextEntity findOcrOrThrow(Long id) {
        return sectionOcrTextRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OCR text not found with id: " + id));
    }

    private SectionOcrTextResponse toResponse(SectionOcrTextEntity entity) {
        return SectionOcrTextResponse.builder()
                .id(entity.getId())
                .sectionId(entity.getSection().getId())
                .sourceFile(entity.getSourceFile())
                .extractedText(entity.getExtractedText())
                .confidence(entity.getConfidence())
                .language(entity.getLanguage())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
