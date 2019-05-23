package com.jeksvp.goalkeeper.domain.entity;

import com.jeksvp.goalkeeper.web.dto.request.CreateProgressRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "goal")
@ToString(exclude = "goal")
@Entity(name = "t_progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_goal")
    private Goal goal;

    @Column(name = "current_value")
    private Float currentValue = 0f;

    @Column(name = "max_value")
    private Float maxValue;

    public static Progress of(CreateProgressRequest request) {
        Progress progress = new Progress();
        progress.setMaxValue(request.getMaxValue());
        return progress;
    }

    public static List<Progress> of(List<CreateProgressRequest> requests) {
        return requests.stream().map(Progress::of).collect(Collectors.toList());
    }
}
