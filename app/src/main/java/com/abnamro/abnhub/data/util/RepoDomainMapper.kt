package com.abnamro.abnhub.data.util

import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.util.Mapper

/**
 * Based on CLEAN Architecture
 * This class is using for mapping Database Model into Domain Entity
 */
class RepoDomainMapper : Mapper<RepoModel, Repo> {

    override fun map(t: RepoModel): Repo {
        return Repo(
            name = t.name,
            avatarUrl = t.avatarUrl,
            visibility = t.visibility,
            isPrivate = t.isPrivate,
        )
    }
}
