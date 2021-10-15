package com.example.visit;

import com.example.SimpleRepo;

public class VisitRepo extends SimpleRepo<VisitId, Visit> {
    public VisitRepo() {
        super(VisitId.class);
    }
}
