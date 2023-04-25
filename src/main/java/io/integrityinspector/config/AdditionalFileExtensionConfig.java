package io.integrityinspector.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AdditionalFileExtensionConfig {
    private Set<String> extensions;
    private String existingExtension;
}
