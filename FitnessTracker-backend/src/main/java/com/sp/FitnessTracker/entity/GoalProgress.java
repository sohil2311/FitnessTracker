package com.sp.FitnessTracker.entity;

public class GoalProgress {
    private Goal goal;
    private double progress;

    public GoalProgress(Goal goal, double progress) {
        this.goal = goal;
        this.progress = progress;
    }

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

    
}
