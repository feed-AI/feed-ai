package com.feed.ai.core.service.impl;

import com.feed.ai.core.dto.SimilarityResponse;
import com.feed.ai.core.exception.NewsAnalyzerException;
import com.feed.ai.core.service.NewsServiceFinder;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NewsServiceFinderImpl implements NewsServiceFinder {

    private static final NepaliAnalyzer analyzer = new NepaliAnalyzer();

    @Override
    public SimilarityResponse calculateAllSimilarities(String content1, String content2) {
        SimilarityResponse similarityResults = new SimilarityResponse();

        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        Map<CharSequence, Integer> vector1 = getTermFrequencies(content1);
        Map<CharSequence, Integer> vector2 = getTermFrequencies(content2);

        double cosine = cosineSimilarity.cosineSimilarity(vector1, vector2);
        similarityResults.setCosineSimilarity(cosine);

        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        double jaccard = jaccardSimilarity.apply(content1, content2);
        similarityResults.setJaccardSimilarity(jaccard);

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int levenshtein = levenshteinDistance.apply(content1, content2);
        double maxLen = Math.max(content1.length(), content2.length());

        double levenshteinSimilarity = maxLen == 0 ? 1.0 : 1 - (levenshtein / maxLen);
        similarityResults.setLevenshteinSimilarity(levenshteinSimilarity);

        return similarityResults;
    }

    private Map<CharSequence, Integer> getTermFrequencies(String content) {
        Map<CharSequence, Integer> wordFreq = new HashMap<>();
        try {
            var tokenStream = analyzer.tokenStream(null, content);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = tokenStream.getAttribute(CharTermAttribute.class).toString();
                wordFreq.put(term, wordFreq.getOrDefault(term, 0) + 1);
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            throw new NewsAnalyzerException("Error getting frequency from words from news", e);
        }
        return wordFreq;
    }

}
