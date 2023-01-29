package org.plagiarism.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalFileExtensionConfig {
    private Set<String> extensions;
    private String existingExtension;
}
