package com.abnamro.abnhub.data.util

import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.domain.util.Mapper

/**
 * Based on CLEAN Architecture
 * This class is using for mapping Database Model into Domain Entity
 */
class RepoDetailDomainMapper : Mapper<RepoModel, RepoDetail> {

    override fun map(t: RepoModel): RepoDetail {
        return RepoDetail(
            name = t.name,
            fullName = t.fullName,
            description = t.description,
            avatarUrl = t.avatarUrl,
            visibility = t.visibility,
            isPrivate = t.isPrivate,
        )
    }
}
