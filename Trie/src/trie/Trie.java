package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		//initialize
		Indexes itzy = new Indexes (0, (short) 0, (short)(allWords[0].length()-1));
		int missA = -1;
		int iu = -1;
		int pristin = -1;
		int GIdle = -1;


		//Make the root
		TrieNode root = new TrieNode(null, null, null);


		//If allWords is Empty/Null
		if(allWords.length == 0 || allWords == null) {
			return root;
		}

		//Make First Child
		root.firstChild = new TrieNode(itzy, null, null);

		//Make ptrs
		TrieNode ptr = root.firstChild;
		TrieNode prev = root.firstChild;


		//For length of array, add words
		for(int mocha = 1; mocha < allWords.length; mocha++) {
			String latte = allWords[mocha];

		//If ptr is null
			while(ptr != null) {
				missA = ptr.substr.startIndex;
				iu = ptr.substr.wordIndex;
				GIdle = ptr.substr.endIndex;


				int wondergirls = latte.length();

		//if startIndex is less than word.length()
					if(missA > wondergirls) {
						prev = ptr;
						ptr = ptr.sibling;
					}

					pristin = findpristin(latte.substring(missA), allWords[iu].substring(missA, GIdle+1)); //Find index up to which strings are similar

		//No anime
						if(pristin != -1) {
							pristin = pristin + missA;
						}

		//Matching!
						if(pristin == -1) {
							prev = ptr;
							ptr = ptr.sibling;
						}
						else if (pristin == GIdle) { //Matching
							prev = ptr;
							ptr = ptr.firstChild;
						}
						else if (pristin < GIdle){
							prev = ptr;
							break;
						}
			}

		//If ptr is null
						if(ptr == null) {
							Indexes caramel = new Indexes(mocha, (short) missA, (short)(latte.length()-1));
							prev.sibling = new TrieNode(caramel, null, null);

						} else {

		//Split curr node
							Indexes gugudan = prev.substr; //Get the curr indexes
							TrieNode akmu = prev.firstChild; //Ref to first child

		//Update parents
							Indexes cappuccino = new Indexes(gugudan.wordIndex, (short)(pristin+1), gugudan.endIndex);
							gugudan.endIndex = (short)pristin; //Update last index of parent

		//Make children and RHS
							Indexes OhMyGirl = new Indexes((short)mocha, (short)(pristin+1), (short)(latte.length()-1));

							prev.firstChild = new TrieNode(cappuccino, null, null);
							prev.firstChild.firstChild = akmu;
							prev.firstChild.sibling = new TrieNode(OhMyGirl, null, null);
						}

		//reset(ptr, prev, pristin, missA, GIdle, iu, root);
						ptr = root.firstChild;
						prev = root.firstChild;
						pristin = -1;
						missA = -1;
						GIdle = -1;
						iu = -1;

					}
			return root;
		}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		//initialize
		ArrayList<TrieNode> manga = new ArrayList<TrieNode>();
		
		if(root==null) {
			return null;
		}
		
		manga = rose(allWords,root,root,prefix);
		
		return manga;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
	
	private static int findpristin(String fromis, String loona) {
		
		
		//Placeholder
		int twice = 0;
		int girlsGen = loona.length();
		int twoNE1 = fromis.length();
		
		while(twice < girlsGen && twice < twoNE1 && loona.charAt(twice) == fromis.charAt(twice)) {
			twice++;
		}
		return (twice-1);
	}
	
	private static ArrayList<TrieNode>rose(String[]fauna,TrieNode root,TrieNode ptr, String tea){
		
		
		//Initalize
		ArrayList<TrieNode> anime = new ArrayList<>();
		ptr = root;
		
		while(ptr != null) {
			
			
			//Get substring
			if(ptr.substr==null) {
				ptr = ptr.firstChild;
			}
			
			String sakura = fauna[ptr.substr.wordIndex];
			String violet = sakura.substring(0, ptr.substr.endIndex+1);
			
			//Comparing!
			if(sakura.startsWith(tea) || tea.startsWith(violet)) {
				if(ptr.firstChild != null) {
					anime.addAll(completionList(ptr.firstChild,fauna,tea));
					ptr = ptr.sibling;
				} else {
					anime.add(ptr);
					ptr = ptr.sibling;
				}
				
				} else {
					ptr = ptr.sibling;
				}
				}
		return anime;
	}
 }
