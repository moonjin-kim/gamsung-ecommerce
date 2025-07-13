package com.loopers.domain.point;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Point extends BaseEntity {
    int amount;
    PointStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Point charge(User user, int amount) {
        Point point = new Point();

        return point;
    }
}
