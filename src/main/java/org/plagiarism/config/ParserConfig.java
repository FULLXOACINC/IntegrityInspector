package org.plagiarism.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ParserConfig {
    private List<AdditionalFileExtensionConfig> additionalFileExtensions;
}
