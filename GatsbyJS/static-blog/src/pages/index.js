import React, { useState } from "react"
import { Link, graphql } from "gatsby"
import TemplateWrapper from "../layouts"

const IndexPage = ({ data }) => {
  const [state, setState] = useState({
    filteredPosts: [],
    query: "",
  })

  const allPosts = data.allMarkdownRemark.edges

  const handleInputChange = event => {
    const query = event.target.value
    const filteredPosts = allPosts.filter(post => {
      const { title } = post.node.frontmatter
      return title.toLowerCase().includes(query.toLowerCase())
    })
    setState({
      query,
      filteredPosts,
    })
  }

  const posts = state.query ? state.filteredPosts : allPosts

  return (
    <TemplateWrapper>
      <h1>Posts</h1>
      <input
        type="text"
        aria-label="Search"
        placeholder="Search posts"
        value={state.query}
        onChange={handleInputChange}
      />
      <div className="row g-3">
        {posts.map(({ node }, id) => (
          <div key={id} className="article-box">
            <Link
              to={node.fields.slug}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <h3 className="title">{node.frontmatter.title}</h3>
            </Link>
            <p className="author">
              Author: <i>{node.frontmatter.author}</i>
            </p>
            <p className="date">
              {node.frontmatter.date} {node.timeToRead}min read
            </p>
            <p className="excerpt">{node.excerpt}</p>
          </div>
        ))}
      </div>
    </TemplateWrapper>
  )
}

export const query = graphql`
  query HomePageQuery {
    allMarkdownRemark(sort: { fields: [frontmatter___date], order: DESC }) {
      totalCount
      edges {
        node {
          fields {
            slug
          }
          frontmatter {
            title
            date
            author
          }
          excerpt
          timeToRead
        }
      }
    }
  }
`

export default IndexPage
