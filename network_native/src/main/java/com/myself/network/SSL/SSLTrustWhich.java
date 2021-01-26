package com.myself.network.SSL;

public enum SSLTrustWhich {
    TrustAll,//任信所有证书
    JustTrustMe,//只信任自己服务器的证书
    TrustMeTwoway // 双向认证
}
