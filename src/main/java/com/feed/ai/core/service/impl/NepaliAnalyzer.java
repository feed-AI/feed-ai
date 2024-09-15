package com.feed.ai.core.service.impl;

import com.feed.ai.core.exception.NewsAnalyzerException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.CharArraySet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class NepaliAnalyzer extends Analyzer {

    private static final CharArraySet STOP_WORD_SET = loadStopWords();

    private static CharArraySet loadStopWords() {
        Set<String> stopWords = new HashSet<>();
        try {
            ClassPathResource resource = new ClassPathResource("/localization/nepali_stopwords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            throw new NewsAnalyzerException("Error reading the nepali_stopwords txt file", e);
        }
        return new CharArraySet(stopWords, true);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer tokenizer = new StandardTokenizer();

        TokenStream tokenStream = new LowerCaseFilter(tokenizer);
        tokenStream = new StopFilter(tokenStream, STOP_WORD_SET);

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
