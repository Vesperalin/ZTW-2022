import React from "react"
import Helmet from "react-helmet"
import Header from "../components/Header"
import "./index.css"
import "bootstrap/dist/css/bootstrap.min.css"

const TemplateWrapper = ({ children }) => (
  <div>
    <Helmet
      title="Static Blog"
      meta={[
        { name: "description", content: "Sample" },
        { name: "keywords", content: "sample, something" },
      ]}
    />
    <Header />
    <div className="container mt-3">{children}</div>
  </div>
)

export default TemplateWrapper
