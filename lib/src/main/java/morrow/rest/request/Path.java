package morrow.rest.request;

import morrow.path.UncategorisedSegment;

import java.util.List;

public record Path(List<UncategorisedSegment> segments) {
}
