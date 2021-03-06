/*
 * Copyright 2013 bits of proof zrt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitsofproof.supernode.api;

import java.util.List;
import java.util.Set;

import com.bitsofproof.supernode.common.ExtendedKey;

/**
 * This is the API extensions to the bitsofproof supernode should build on
 */
public interface BCSAPI
{
	/**
	 * returns nounce while doing a full roundtrip to the server
	 * 
	 * @param nonce
	 * @return
	 * @throws BCSAPIException
	 */
	public long ping (long nonce) throws BCSAPIException;

	/**
	 * sets the alert listener for the connections
	 * 
	 * @param listener
	 * @throws BCSAPIException
	 */
	public void addAlertListener (AlertListener listener) throws BCSAPIException;

	public void removeAlertListener (AlertListener listener);

	/**
	 * Are we talking to production?
	 * 
	 * @return
	 * @throws BCSAPIException
	 */
	public boolean isProduction () throws BCSAPIException;

	/**
	 * get block header for the hash
	 * 
	 * @param hash
	 * @return block header or null if hash is unknown
	 * @throws BCSAPIException
	 */
	public Block getBlockHeader (String hash) throws BCSAPIException;

	/**
	 * get block for the hash
	 * 
	 * @param hash
	 * @return block or null if hash is unknown
	 * @throws BCSAPIException
	 */
	public Block getBlock (String hash) throws BCSAPIException;

	/**
	 * get the transaction identified by the hash on the trunk
	 * 
	 * @param hash
	 * @return transaction or null if no transaction with that hash on the trunk
	 * @throws BCSAPIException
	 */
	public Transaction getTransaction (String hash) throws BCSAPIException;

	/**
	 * send a signed transaction
	 * 
	 * @param transaction
	 * @throws BCSAPIException
	 */
	public void sendTransaction (Transaction transaction) throws BCSAPIException;

	/**
	 * Register a reject message listener. Effective if connected to a peer with protocol version >= 70002 and in slave mode
	 * 
	 * @param rejectListener
	 * @throws BCSAPIException
	 */
	public void registerRejectListener (RejectListener rejectListener) throws BCSAPIException;

	public void removeRejectListener (RejectListener rejectListener);

	/**
	 * send a mined block
	 * 
	 * @param block
	 * @throws BCSAPIException
	 */
	public void sendBlock (Block block) throws BCSAPIException;

	/**
	 * Register a transactions listener
	 * 
	 * @param listener
	 *            will be called for every validated transaction
	 * @throws BCSAPIException
	 */
	public void registerTransactionListener (TransactionListener listener) throws BCSAPIException;

	/**
	 * remove a listener for validated transactions
	 * 
	 * @param listener
	 */
	public void removeTransactionListener (TransactionListener listener);

	/**
	 * Register a block listener
	 * 
	 * @param listener
	 *            will be called for every validated new block
	 * @throws BCSAPIException
	 */
	public void registerTrunkListener (TrunkListener listener) throws BCSAPIException;

	/**
	 * remove a trunk listener previously registered
	 * 
	 * @param listener
	 */
	public void removeTrunkListener (TrunkListener listener);

	/**
	 * Scan transactions using an address in the given set
	 * 
	 * @param addresses
	 *            - address set
	 * @param after
	 *            - a time point after which the block chain is scanned. The time point is miliseconds in the Unix epoch. The parameter can optimize chain
	 *            lookup, as of current version it is ignored.
	 * @param listener
	 *            - the transaction listener will be called for all transactions found. This call will not return until the listener is called for all
	 *            transactions identified
	 * @throws BCSAPIException
	 */
	public void scanTransactionsForAddresses (Set<Address> addresses, long after, TransactionListener listener)
			throws BCSAPIException;

	/**
	 * Scan transactions for reference of any address of a master key.
	 * 
	 * @param master
	 *            - public master key
	 * @param firstIndex
	 *            - index of the first key of the master key that might be used on the chain after time point, usually 0
	 * @param lookAhead
	 *            - look ahead window while scanning for addresses. The server assumes that the gap between consecutive addresses of the master key used on the
	 *            block chain is not bigger than lookAhead.
	 * @param after
	 *            - a time point after which the block chain is scanned. The time point is miliseconds in the Unix epoch. The parameter can optimize chain
	 *            lookup in conjunction with firstIndex. As of current version parameter after is ignored.
	 * @param listener
	 * @throws BCSAPIException
	 */
	public void scanTransactions (ExtendedKey master, int firstIndex, int lookAhead, long after, TransactionListener listener) throws BCSAPIException;

	/**
	 * Scan unspent transactions using generic binary match.
	 * 
	 * @param match
	 * @param listener
	 * @throws BCSAPIException
	 */
	public void scanUTXOForAddresses (Set<Address> addresses, TransactionListener listener) throws BCSAPIException;

	/**
	 * Generate a trunk update to cach up from current inventory
	 * 
	 * @param inventory
	 *            of block hashes known, highest first
	 * @param limit
	 *            maximum number of blocks or header expected
	 * @param headers
	 *            indicate if headers or full blocks are expected
	 * @param listener
	 * @throws BCSAPIException
	 */
	public void catchUp (List<String> inventory, int limit, boolean headers, TrunkListener listener) throws BCSAPIException;
}
