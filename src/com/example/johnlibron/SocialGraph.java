package com.example.johnlibron;

import java.util.ArrayList;
import java.util.List;

public class SocialGraph {
	
	private boolean[][] graph;

	public SocialGraph() {
		graph = new boolean[10][10];
		
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (i == j) {
					graph[i][j] = true;
				} else {
					graph[i][j] = false;
				}
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (i != j && graph[i][j]) {
					sb.append(i + "-" + j + ", ");
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.lastIndexOf(",")).toString();
		}
		return sb.toString();
	}
	
	private void addFriend(int i, int j) {
		if (i != j) {
			graph[i][j] = true;
		}
	}
	
	private int countFriends(int i) {
		int count = 0;
		for (int j = 0; j < graph[i].length; j++) {
			if (i != j && graph[i][j]) {
				count++;
			}
		}
		return count;
	}
	
	private int countEnemies(int i) {
		int count = 0;
		for (int j = 0; j < graph[i].length; j++) {
			if (i != j && !graph[i][j]) {
				count++;
			}
		}
		return count;
	}
	
	private int countCommonFriends(int i, int j) {
		List<Integer> listOne = new ArrayList<>(); 
		List<Integer> listTwo = new ArrayList<>();
		for (int col = 0; col < graph[i].length; col++) {
			if (i != col && graph[i][col]) {
				listOne.add(col);
			}
			if (j != col && graph[j][col]) {
				listTwo.add(col);
			}
		}
		listOne.retainAll(listTwo);
		return listOne.size();
	}
	
	private int countCommonEnemies(int i, int j) {
		List<Integer> listOne = new ArrayList<>(); 
		List<Integer> listTwo = new ArrayList<>(); 
		for (int col = 0; col < graph[i].length; col++) {
			if (i != col && !graph[i][col]) {
				listOne.add(col);
			}
			if (j != col && !graph[j][col]) {
				listTwo.add(col);
			}
		}
		listOne.retainAll(listTwo);
		return listOne.size();
	}
	
	private int findBestFriends(int i) {
		int bestFriend = -1;
		int friends = 0;
		for (int row = 0; row < graph.length; row++) {
			if (i != row) {
				int count = countCommonFriends(i, row);
				if (count > friends) {
					bestFriend = row;
					friends = count;
				}
			}
		}
		if (bestFriend == -1) {
			bestFriend = i;
		}
		return bestFriend;
	}
	
	private int friendliest() {
		List<Integer> friendsList = new ArrayList<>(); 
		int friendliest = -1;
		int friends = 0;
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (i != j && graph[i][j]) {
					friendsList.add(j);
				}
			}
			if (!friendsList.isEmpty() && friendsList.size() > friends) {
				friends = friendsList.size();
				friendliest = i;
			}
			friendsList.clear();
		}
		return friendliest;
	}
	
	private int loner() {
		List<Integer> friendsList = new ArrayList<>(); 
		int loner = -1;
		int friends = graph[0].length - 1;
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (i != j && graph[i][j]) {
					friendsList.add(j);
				}
			}
			if (!friendsList.isEmpty() && friendsList.size() < friends) {
				friends = friendsList.size();
				loner = i;
			}
			friendsList.clear();
		}
		return loner;
	}
	
	private boolean recluse(int i) {
		for (int j = 0; j < graph[i].length; j++) {
			if (i != j && graph[i][j]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean famous(int i) {
		for (int j = 0; j < graph[i].length; j++) {
			if (i != j && !graph[i][j]) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String args[]) {
		SocialGraph socialGraph = new SocialGraph();
		
		socialGraph.addFriend(1, 5);
		socialGraph.addFriend(1, 2);
		socialGraph.addFriend(1, 3);
		socialGraph.addFriend(1, 4);
		socialGraph.addFriend(1, 9);
		socialGraph.addFriend(0, 8);
		socialGraph.addFriend(0, 7);
		socialGraph.addFriend(1, 7);
		socialGraph.addFriend(1, 6);
		socialGraph.addFriend(1, 0);
		socialGraph.addFriend(1, 8);
		socialGraph.addFriend(0, 9);
		socialGraph.addFriend(5, 4);
		socialGraph.addFriend(5, 9);
		socialGraph.addFriend(5, 7);
		socialGraph.addFriend(5, 1);
		socialGraph.addFriend(4, 7);
		socialGraph.addFriend(4, 5);
		socialGraph.addFriend(4, 1);
		socialGraph.addFriend(4, 0);
		socialGraph.addFriend(9, 1);

		System.out.println("Social Group: " + socialGraph.toString());
		
		System.out.println("Count friends of '5' : " + socialGraph.countFriends(5));
		System.out.println("Count enemies of '5' : " + socialGraph.countEnemies(5));
		
		System.out.println("Count common friends of '5 and 4' : " + socialGraph.countCommonFriends(5, 4));
		System.out.println("Count common enemies of '5 and 4' : " + socialGraph.countCommonEnemies(5, 4));
		
		System.out.println("Best friend of '4' in the group : " + socialGraph.findBestFriends(4));
		
		int friendliest = socialGraph.friendliest();
		if (friendliest > -1) {
			System.out.println("Friendlist in the group : " + friendliest);
		} else {
			System.out.println("Friendlist in the group : none");
		}
		
		int loner = socialGraph.loner();
		if (loner > -1) {
			System.out.println("Loner in the group : " + loner);
		} else {
			System.out.println("Loner in the group : none");
		}
		
		System.out.println("Is '9' recluse? " + (socialGraph.recluse(9) ? "Yes" : "No"));
		System.out.println("Is '8' recluse? " + (socialGraph.recluse(8) ? "Yes" : "No"));
		
		System.out.println("Is '1' famous? " + (socialGraph.famous(1) ? "Yes" : "No"));
		System.out.println("Is '4' famous? " + (socialGraph.famous(4) ? "Yes" : "No"));
	}
}
