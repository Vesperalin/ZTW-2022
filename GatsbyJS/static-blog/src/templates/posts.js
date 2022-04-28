import React from "react"
import { graphql } from "gatsby"
import { StaticImage, GatsbyImage, getImage } from "gatsby-plugin-image"
import TemplateWrapper from "../layouts"
import { Disqus } from "gatsby-plugin-disqus"

const Post = ({ data }) => {
  const post = data.markdownRemark

  if (!post) {
    return "Loading"
  } else {
    const image = getImage(post.frontmatter.photo)
    return (
      <TemplateWrapper>
        <div className="col-12">
          <GatsbyImage
            className="img-fluid"
            image={image}
            alt={post.frontmatter.title}
          />
          <h1>{post.frontmatter.title}</h1>
          <h4 className="text-muted">
            {post.frontmatter.author}{" "}
            <span style={{ fontSize: "0.8em" }}> -{post.frontmatter.date}</span>
          </h4>
          <div
            className="mt-5"
            dangerouslySetInnerHTML={{ __html: post.html }}
          />
          <Disqus
            config={{
              /* Replace PAGE_URL with your post's canonical URL variable */
              url: `http://localhost:8000/${post.frontmatter.title}`,
              /* Replace PAGE_IDENTIFIER with your page's unique identifier variable */
              identifier: post.frontmatter.title,
              /* Replace PAGE_TITLE with the title of the page */
              title: post.frontmatter.title,
            }}
          />
        </div>
      </TemplateWrapper>
    )
  }
}
export const query = graphql`
  query PostQuery($slug: String!) {
    markdownRemark(fields: { slug: { eq: $slug } }) {
      html
      frontmatter {
        title
        author
        date
        photo {
          childImageSharp {
            gatsbyImageData
          }
        }
      }
    }
  }
`
export default Post
