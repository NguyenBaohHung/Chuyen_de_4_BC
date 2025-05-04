// utils/cryptoPayment.js
import { ethers } from 'ethers';

export const handleCryptoPayment = async ({
                                              receiverWallet,
                                              amountInEth,
                                              onSuccess,
                                              onError,
                                          }) => {
    if (!window.ethereum) {
        alert("Vui lòng cài MetaMask trước!");
        return;
    }

    try {
        const provider = new ethers.providers.Web3Provider(window.ethereum);
        await provider.send("eth_requestAccounts", []);
        const signer = provider.getSigner();

        const tx = await signer.sendTransaction({
            to: receiverWallet,
            value: ethers.utils.parseEther(amountInEth),
        });

        if (onSuccess) onSuccess(tx);
    } catch (err) {
        console.error("Lỗi khi thanh toán:", err);
        if (onError) onError(err);
    }
};
