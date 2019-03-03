package com.masterpeace.atmosphere.dto;

import com.masterpeace.atmosphere.model.Limit;

import java.util.List;

/**
 *
 */
public final class UserLimitDTO {

    private final long userId;
    private final List<Limit> limits;

    public UserLimitDTO(final long userId, final List<Limit> limits) {
        this.userId = userId;
        this.limits = limits;
    }

    public long getUserId() {
        return userId;
    }

    public List<Limit> getLimits() {
        return limits;
    }
}
