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
public class ProgrammingLangLineStartExclusion {
    private Set<String> cpp;
    private Set<String> cSharp;
    private Set<String> java;
    private Set<String> python;
    private Set<String> js;
}
