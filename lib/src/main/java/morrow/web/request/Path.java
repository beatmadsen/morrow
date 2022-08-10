package morrow.web.request;

import morrow.web.path.UncategorisedSegment;

import java.util.List;

public record Path(List<UncategorisedSegment> segments) {
}
