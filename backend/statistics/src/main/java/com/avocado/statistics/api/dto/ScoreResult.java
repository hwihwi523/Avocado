package com.avocado.statistics.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreResult implements Comparable<ScoreResult> {
    private long merchandiseId;
    private long score;

    @Override
    public int compareTo(ScoreResult o) {
        return Long.compare(this.score, o.getScore());
    }
}
